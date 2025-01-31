variable "CAMUNDA_CLUSTER_REGION" {
  description = "Camunda Cluster Region"
  type        = string
}

variable "CAMUNDA_CLUSTER_ID" {
  description = "Camunda Cluster ID"
  type        = string
}

variable "CAMUNDA_CLIENT_ID" {
  description = "Camunda Client ID"
  type        = string
}

variable "CAMUNDA_CLIENT_SECRET" {
  description = "Camunda Client Secret"
  type        = string
}

variable "CAMUNDA_CLIENT_MODE" {
  description = "Camunda Client Mode"
  type        = string
}

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
  namespace  = "grafana"
}

module "fibonacci-worker" {
  source = "./modules/worker"
  depends_on = [ module.prometheus ]
  CAMUNDA_CLUSTER_REGION = var.CAMUNDA_CLUSTER_REGION
  CAMUNDA_CLUSTER_ID     = var.CAMUNDA_CLUSTER_ID
  CAMUNDA_CLIENT_ID      = var.CAMUNDA_CLIENT_ID
  CAMUNDA_CLIENT_SECRET  = var.CAMUNDA_CLIENT_SECRET
  CAMUNDA_CLIENT_MODE    = var.CAMUNDA_CLIENT_MODE
}