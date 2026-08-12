variable "subscription_id" {
  description = "Azure subscription ID"
  type        = string
}

variable "resource_group_name" {
  description = "Existing HealthConnect resource group"
  type        = string
  default     = "rg-healthconnect-acr"
}

variable "db_username" {
  description = "Demo database username for Key Vault"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "Demo database password for Key Vault"
  type        = string
  sensitive   = true
}