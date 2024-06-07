resource "helm_release" "camunda-platform" {
  name       = "camunda-platform"
  chart      = "camunda-platform"
  repository = "https://helm.camunda.io"
  namespace = "camunda"
  create_namespace = true

  values = [
    "${file("./values/c8-values-8.5.2.yaml")}"
  ]
  
  /* In case of using the latest values from c8
  values = [
    "${data.http.latest_camunda_values.response_body}"
  ]
  */

  wait = true
  timeout = 600000000
  
  depends_on = [null_resource.merge_kubeconfig, data.http.latest_camunda_values]
}

data "http" "latest_camunda_values" {
  url = "https://helm.camunda.io/camunda-platform/values/values-latest.yaml"

  request_headers = {
    Accept = "text/yaml"
  }
}

// deploy configmap with process via zbctl
resource "kubernetes_job_v1" "bpmnmodeldeployment" {
  depends_on = [ kubernetes_config_map_v1.bpmnmodel, helm_release.camunda-platform ]
  metadata {
    name = "bpmndeployment"
    namespace = "camunda"
  }
  spec {
    template {
      metadata {}
      spec {
        container {
          name  = "bpmndeployment"
          image = "sitapati/zbctl:8.2.10"
          

          env {
            name  = "ZEEBE_ADDRESS"
            value = "camunda-platform-zeebe-gateway:26500"
          }

          command = ["/zbctl"]
          args = [ "deploy", "resource", "/process/Fibonacciprocess.bpmn" , "--insecure"]

          // for future testing purposes I'll leave this here: 
          //image = "alpine:3.14"
          //command = ["bin/cat"]
          //args = [ "/process/example_process.bpmn" ]

          volume_mount {
            name = "bpmnmodelvolume"
            mount_path = "/process"
          }
        }

        restart_policy = "Never"

        volume {
          name = "bpmnmodelvolume"
          config_map {
            name = "bpmnmodeldata"
          }
        }
      }
    }
    backoff_limit = 1
  }
  wait_for_completion = true
}

// create configmap to hold process file
resource "kubernetes_config_map_v1" "bpmnmodel" {
  depends_on = [ helm_release.camunda-platform ]
  metadata {
    name = "bpmnmodeldata"
    namespace = "camunda"
  }

  data = {
    "Fibonacciprocess.bpmn" = file("${abspath(path.module)}/../src/fibonacci-process/Fibonacciprocess.bpmn")
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
  depends_on = [ kubernetes_job_v1.bpmnmodeldeployment ]
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