resource "helm_release" "camunda-platform" {
  name             = "camunda-platform"
  chart            = "camunda-platform"
  repository       = "https://helm.camunda.io"
  namespace        = var.namespace
  create_namespace = true
  count            = (var.minimal_config || var.saas ) ? 0 : 1
  timeout = 600000

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
  namespace        = var.namespace
  create_namespace = true
  count            = (var.minimal_config && !var.saas) ? 1 : 0
  timeout = 600000

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