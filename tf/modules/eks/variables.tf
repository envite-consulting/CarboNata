variable "create_module" {
  default = true
  type    = bool
}

variable "region" {
  default = "eu-central-1"
  type    = string
}

variable "camunda-cluster-name" {
  default = "carbonata-cluster"
  type    = string
}
