variable "minimal_config" {
  default = true
  type    = bool
}

variable "namespace" {
  default = "camunda"
  type = string
}

variable "saas" {
  description = "Indicates whether camunda SaaS version is being used"
  default     = true
  type        = bool
}