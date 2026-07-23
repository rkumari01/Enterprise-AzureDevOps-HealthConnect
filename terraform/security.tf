
resource "azurerm_network_security_group" "aks_nsg" {

  name                = "nsg-${lower(var.project_name)}-${var.environment}"

  location            = azurerm_resource_group.rg.location

  resource_group_name = azurerm_resource_group.rg.name

  tags = local.common_tags

}
resource "azurerm_subnet_network_security_group_association" "aks" {

  subnet_id                 = azurerm_subnet.aks_subnet.id

  network_security_group_id = azurerm_network_security_group.aks_nsg.id

}
resource "azurerm_network_security_rule" "allow_https" {

  name                        = "Allow-HTTPS"

  priority                    = 100

  direction                   = "Inbound"

  access                      = "Allow"

  protocol                    = "Tcp"

  source_port_range           = "*"

  destination_port_range      = "443"

  source_address_prefix       = "*"

  destination_address_prefix  = "*"

  resource_group_name         = azurerm_resource_group.rg.name

  network_security_group_name = azurerm_network_security_group.aks_nsg.name

}