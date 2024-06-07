resource "helm_release" "prometheus" {
  name       = "kube-prometheus-stack"
  repository = "https://prometheus-community.github.io/helm-charts"
  chart      = "kube-prometheus-stack"
  namespace  = "grafana"
  create_namespace = true
  version    = "45.7.1"


  values = [
    "${file("./values/prometheus-values.yaml")}"
  ]

}