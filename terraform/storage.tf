
resource "azurerm_storage_account" "tfstate" {

  name                     = "st${lower(var.project_name)}${var.environment}001"

  resource_group_name      = azurerm_resource_group.rg.name

  location                 = azurerm_resource_group.rg.location

  account_tier             = "Standard"

  account_replication_type = "LRS"

  tags = local.common_tags

}