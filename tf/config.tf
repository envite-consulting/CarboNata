variable "region" {
  default = "eu-central-1"
  type = string
}

variable "camunda-cluster-name" {
    default = "carbonata-cluster"
    type = string
}

variable "kubernetes_local" {
  default = true
  type = bool
}