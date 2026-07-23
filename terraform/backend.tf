
terraform {

  backend "azurerm" {

    resource_group_name  = "rg-healthconnect-dev"

    storage_account_name = "sthealthconnectdev001"

    container_name       = "tfstate"

    key                  = "terraform.tfstate"

  }

}