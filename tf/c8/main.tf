variable "saas" {
  description = "Indicates whether camunda SaaS version is being used"
  type        = bool
  default     = true
}

module "kepler" {
  source     = "./modules/kepler"
  depends_on = [module.prometheus]
  namespace  = "grafana"
}

module "camunda" {
  source         = "./modules/camunda"
  minimal_config = true
  saas = var.saas
}

module "prometheus" {
  source     = "./modules/prometheus"
  depends_on = [ module.camunda ]
  namespace  = "grafana"
}

module "fibonacci-worker" {
  source = "./modules/worker"
}


