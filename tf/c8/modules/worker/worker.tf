resource "helm_release" "fibonacci-worker" {
  name       = "fibonacci-worker"
  chart      = "../../src/fibonacci-worker/helm"
  namespace  = var.namespace
  create_namespace = true
  version    = 0.1
  count      = var.create_module ? 1 : 0

  values = [
    "${file("${abspath(path.module)}/values/values.yaml")}"
  ]
}