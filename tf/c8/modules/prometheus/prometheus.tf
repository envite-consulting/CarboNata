resource "helm_release" "prometheus" {
  name       = "kube-prometheus-stack"
  repository = "https://prometheus-community.github.io/helm-charts"
  chart      = "kube-prometheus-stack"
  namespace  = var.namespace
  create_namespace = true
  version    = "45.7.1"


  values = [
    "${file("${abspath(path.module)}/values/prometheus-values.yaml")}"
  ]

}