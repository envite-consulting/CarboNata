resource "helm_release" "camunda-platform" {
  name             = "camunda-platform"
  chart            = "camunda-platform"
  repository       = "https://helm.camunda.io"
  namespace        = "camunda"
  create_namespace = true
  count            = var.minimal_config ? 0 : 1
  timeout = 200000000000

  values = [
    "${file("${abspath(path.module)}/values/c8-values-8.5.2.yaml")}"
  ]

  /* In case of using the latest values from c8
  values = [
    "${data.http.latest_camunda_values.response_body}"
  ]
  */

  depends_on = [data.http.latest_camunda_values]
}

resource "helm_release" "camunda-platform-minimal" {
  name             = "camunda-platform"
  chart            = "camunda-platform"
  repository       = "https://helm.camunda.io"
  namespace        = "camunda"
  create_namespace = true
  count            = var.minimal_config ? 1 : 0
  timeout = 200000000000

  values = [
    "${file("${abspath(path.module)}/values/camunda-minimal.yaml")}"
  ]

  /* In case of using the latest values from c8
  values = [
    "${data.http.latest_camunda_values.response_body}"
  ]
  */

  depends_on = [data.http.latest_camunda_values]
}

data "http" "latest_camunda_values" {
  url = "https://helm.camunda.io/camunda-platform/values/values-latest.yaml"

  request_headers = {
    Accept = "text/yaml"
  }
}

/*

resource "helm_release" "console-worker" {
  name       = "console-worker"
  chart      = "console-worker-chart"
  repository = "https://maxbehr801.github.io/helmrepotest"
  namespace  = var.namespace
  version    = "0.0.2"
  count      = var.create_module ? 1 : 0
}

resource "kubernetes_job_v1" "processstart" {
  count = var.create_module ? 1 : 0
  metadata {
    name = "processstart"
    namespace = var.namespace
  }
  spec {
    template {
      metadata {}
      spec {
        container {
          name  = "processtart"
          image = "sitapati/zbctl:8.2.10"
          

          env {
            name  = "ZEEBE_ADDRESS"
            value = "camunda-platform-zeebe-gateway:26500"
          }

          command = ["/zbctl"]
          args = [ "create", "instance", "Sample_Process" , "--insecure"]
        }

        restart_policy = "Never"
      }
    }
    backoff_limit = 1
  }
  wait_for_completion = true
} 

*/
