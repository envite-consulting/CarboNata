resource "helm_release" "fibonacci-worker" {
  name       = "fibonacci-worker"
  chart      = "../../src/fibonacci-worker/helm"
  create_namespace = true
  namespace  = var.namespace
  version    = 0.1
  count      = var.create_module ? 1 : 0

  values = [
    "${file("${abspath(path.module)}/values/values.yaml")}"
  ]
}