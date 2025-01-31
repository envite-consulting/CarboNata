resource "helm_release" "fibonacci-worker" {
  name       = "fibonacci-worker"
  chart      = "../../src/fibonacci-worker/helm"
  namespace  = var.namespace
  create_namespace = true
  version    = 0.1
  count      = var.create_module ? 1 : 0

  set {
    name  = "container.env.saasRegion"
    value = var.CAMUNDA_CLUSTER_REGION
  }

  set {
    name  = "container.env.saasClusterId"
    value = var.CAMUNDA_CLUSTER_ID
  }

  set {
    name  = "container.env.saasClientId"
    value = var.CAMUNDA_CLIENT_ID
  }

  set {
    name  = "container.env.saasClientSecret"
    value = var.CAMUNDA_CLIENT_SECRET
  }

  set {
    name  = "container.env.saasClientMode"
    value = var.CAMUNDA_CLIENT_MODE
  }

  values = [
    file("${abspath(path.module)}/values/values.yaml")
  ]
}