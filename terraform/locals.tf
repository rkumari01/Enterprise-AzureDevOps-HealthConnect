locals {

  resource_group_name = "rg-${lower(var.project_name)}-${var.environment}"

  common_tags = {
    Environment = var.environment
    Project     = var.project_name
    Owner       = var.owner
    ManagedBy   = "Terraform"
  }

}