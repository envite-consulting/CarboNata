variable "namespace" {
  default = "worker"
  type = string
}

variable "create_module" {
  type        = bool
  default     = true
}

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