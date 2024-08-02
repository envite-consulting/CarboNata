resource "helm_release" "kepler" {
  name = "kepler"
  chart = "kepler"
  repository = "https://sustainable-computing-io.github.io/kepler-helm-chart"
  namespace = "kepler"
  create_namespace = true

  values = [
    "${file("${abspath(path.module)}/values/kepler-values.yaml")}"
  ]

}

resource "kubernetes_config_map_v1" "kepler_grafana_dashboards" {
  metadata {
    name      = "kepler-grafana-dashboard"
    namespace = "kepler"
    labels = {
      grafana_dashboard : "1"
    }
  }
  data = {
    "Kepler-Exporter.json" = file("${abspath(path.module)}/dashboards/Kepler-Exporter.json")
  }

  depends_on = [ helm_release.kepler ]
}
