output "vnet_name" {
  value = azurerm_virtual_network.healthconnect.name
}

output "vnet_id" {
  value = azurerm_virtual_network.healthconnect.id
}

output "app_subnet_id" {
  value = azurerm_subnet.app.id
}

output "aks_subnet_id" {
  value = azurerm_subnet.aks.id
}

output "private_endpoint_subnet_id" {
  value = azurerm_subnet.private_endpoint.id
}

output "nsg_name" {
  value = azurerm_network_security_group.healthconnect.name
}

output "key_vault_name" {
  value = azurerm_key_vault.healthconnect.name
}

output "key_vault_uri" {
  value = azurerm_key_vault.healthconnect.vault_uri
}

output "managed_identity_client_id" {
  value = azurerm_user_assigned_identity.healthconnect.client_id
}

output "managed_identity_principal_id" {
  value = azurerm_user_assigned_identity.healthconnect.principal_id
}