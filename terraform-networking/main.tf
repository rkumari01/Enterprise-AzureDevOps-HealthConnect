terraform {
  required_version = ">= 1.5.0"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 4.0"
    }
  }
}

provider "azurerm" {
  features {}

  subscription_id = var.subscription_id
}

# ------------------------------------------------------------
# Existing Resource Group
# ------------------------------------------------------------

data "azurerm_resource_group" "healthconnect" {
  name = var.resource_group_name
}

# ------------------------------------------------------------
# Virtual Network
# ------------------------------------------------------------

resource "azurerm_virtual_network" "healthconnect" {
  name                = "healthconnect-vnet"
  location            = data.azurerm_resource_group.healthconnect.location
  resource_group_name = data.azurerm_resource_group.healthconnect.name
  address_space       = ["10.20.0.0/16"]

  tags = {
    Project     = "HealthConnect"
    Environment = "Dev"
    ManagedBy   = "Terraform"
  }
}

# ------------------------------------------------------------
# Network Security Group
# ------------------------------------------------------------

resource "azurerm_network_security_group" "healthconnect" {
  name                = "healthconnect-nsg"
  location            = data.azurerm_resource_group.healthconnect.location
  resource_group_name = data.azurerm_resource_group.healthconnect.name

  tags = {
    Project   = "HealthConnect"
    ManagedBy = "Terraform"
  }
}

# ------------------------------------------------------------
# App Subnet
# ------------------------------------------------------------

resource "azurerm_subnet" "app" {
  name                 = "app-subnet"
  resource_group_name  = data.azurerm_resource_group.healthconnect.name
  virtual_network_name = azurerm_virtual_network.healthconnect.name
  address_prefixes     = ["10.20.1.0/24"]
}

# ------------------------------------------------------------
# AKS Subnet
# ------------------------------------------------------------

resource "azurerm_subnet" "aks" {
  name                 = "aks-subnet"
  resource_group_name  = data.azurerm_resource_group.healthconnect.name
  virtual_network_name = azurerm_virtual_network.healthconnect.name
  address_prefixes     = ["10.20.2.0/23"]
}

# ------------------------------------------------------------
# Private Endpoint Subnet
# ------------------------------------------------------------

resource "azurerm_subnet" "private_endpoint" {
  name                 = "private-endpoint-subnet"
  resource_group_name  = data.azurerm_resource_group.healthconnect.name
  virtual_network_name = azurerm_virtual_network.healthconnect.name
  address_prefixes     = ["10.20.4.0/24"]

  private_endpoint_network_policies = "Disabled"
}

# ------------------------------------------------------------
# Associate NSG with App Subnet
# ------------------------------------------------------------

resource "azurerm_subnet_network_security_group_association" "app" {
  subnet_id                 = azurerm_subnet.app.id
  network_security_group_id = azurerm_network_security_group.healthconnect.id
}

# ------------------------------------------------------------
# Associate NSG with AKS Subnet
# ------------------------------------------------------------

resource "azurerm_subnet_network_security_group_association" "aks" {
  subnet_id                 = azurerm_subnet.aks.id
  network_security_group_id = azurerm_network_security_group.healthconnect.id
}

# ------------------------------------------------------------
# Associate NSG with Private Endpoint Subnet
# ------------------------------------------------------------

resource "azurerm_subnet_network_security_group_association" "private_endpoint" {
  subnet_id                 = azurerm_subnet.private_endpoint.id
  network_security_group_id = azurerm_network_security_group.healthconnect.id
}