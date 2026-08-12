# ------------------------------------------------------------
# User Assigned Managed Identity
# ------------------------------------------------------------

resource "azurerm_user_assigned_identity" "healthconnect" {
  name                = "healthconnect-managed-identity"
  location            = data.azurerm_resource_group.healthconnect.location
  resource_group_name = data.azurerm_resource_group.healthconnect.name

  tags = {
    Project   = "HealthConnect"
    ManagedBy = "Terraform"
  }
}

# ------------------------------------------------------------
# Azure Key Vault
# ------------------------------------------------------------

resource "azurerm_key_vault" "healthconnect" {
  name                = "kv-healthconnect-ritika"
  location            = data.azurerm_resource_group.healthconnect.location
  resource_group_name = data.azurerm_resource_group.healthconnect.name
  tenant_id           = data.azurerm_client_config.current.tenant_id

  sku_name = "standard"

  # RBAC-based authorization
  rbac_authorization_enabled = true

  # Allow Terraform to manage the vault during this lab
  purge_protection_enabled   = false
  soft_delete_retention_days = 7

  tags = {
    Project     = "HealthConnect"
    Environment = "Dev"
    ManagedBy   = "Terraform"
  }
}

# ------------------------------------------------------------
# Allow Managed Identity to read secrets
# ------------------------------------------------------------

resource "azurerm_role_assignment" "keyvault_secrets_user" {
  scope                = azurerm_key_vault.healthconnect.id
  role_definition_name = "Key Vault Secrets User"
  principal_id         = azurerm_user_assigned_identity.healthconnect.principal_id
}

# ------------------------------------------------------------
# Demo application secrets
# ------------------------------------------------------------

resource "azurerm_key_vault_secret" "db_username" {
  name         = "healthconnect-db-username"
  value        = var.db_username
  key_vault_id = azurerm_key_vault.healthconnect.id

  depends_on = [
    azurerm_role_assignment.terraform_keyvault_secrets_officer
  ]
}

resource "azurerm_key_vault_secret" "db_password" {
  name         = "healthconnect-db-password"
  value        = var.db_password
  key_vault_id = azurerm_key_vault.healthconnect.id

  depends_on = [
    azurerm_role_assignment.terraform_keyvault_secrets_officer
  ]
}

# ------------------------------------------------------------
# Current Azure tenant information
# ------------------------------------------------------------

data "azurerm_client_config" "current" {}

# ------------------------------------------------------------
# Allow Terraform/current Azure identity to manage secrets
# ------------------------------------------------------------

resource "azurerm_role_assignment" "terraform_keyvault_secrets_officer" {
  scope                = azurerm_key_vault.healthconnect.id
  role_definition_name = "Key Vault Secrets Officer"
  principal_id         = data.azurerm_client_config.current.object_id

  depends_on = [
    azurerm_key_vault.healthconnect
  ]
}