# ============================================================
# HealthConnect AKS Cluster
# ============================================================

resource "azurerm_kubernetes_cluster" "healthconnect" {
  name                = "aks-healthconnect-ritika"
  location            = data.azurerm_resource_group.healthconnect.location
  resource_group_name = data.azurerm_resource_group.healthconnect.name
  dns_prefix          = "healthconnect"

  default_node_pool {
    name           = "system"
    vm_size        = "Standard_B2s_v2"
    node_count     = 1
    vnet_subnet_id = azurerm_subnet.aks.id
  }

  identity {
    type = "SystemAssigned"
  }

  network_profile {
    network_plugin    = "azure"
    load_balancer_sku = "standard"
  }

  tags = {
    Project     = "HealthConnect"
    ManagedBy   = "Terraform"
    Environment = "Dev"
  }

  depends_on = [
    azurerm_subnet.aks
  ]
}