module "kepler" {
  source     = "./modules/kepler"
  depends_on = [module.prometheus]
  namespace  = "grafana"
}

module "camunda" {
  source         = "./modules/camunda"
  minimal_config = true
  // depends_on = [ module.eks ]
}

/*
module "eks" {   
    source        = "./modules/eks"
    create_module = false 
}*/

module "prometheus" {
  source     = "./modules/prometheus"
  depends_on = [module.camunda]
  namespace  = "grafana"
}
