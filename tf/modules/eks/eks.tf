/*
module "eks_cluster" {
  source = "github.com/camunda/camunda-tf-eks-module/modules/eks-cluster"

  region  = var.region # change to your AWS region
  name    = var.camunda-cluster-name # change to name of your choosing

  # Set CIDR ranges or use the defaults
  cluster_service_ipv4_cidr = "10.190.0.0/16"
  cluster_node_ipv4_cidr    = "10.192.0.0/16"
  # count = var.create_module ? 1 : 0

}

module "postgresql" {
  source                     = "github.com/camunda/camunda-tf-eks-module/modules/aurora"
  engine_version             = "15.4"
  auto_minor_version_upgrade = false
  cluster_name               = "carbonata-postgresql" # change "cluster-name" to your name
  default_database_name      = "camunda"
  # count = var.create_module ? 1 : 0

  # Please supply your own secret values
  username         = "demo"
  password         = "demodemo"
  vpc_id           = module.eks_cluster.vpc_id
  subnet_ids       = module.eks_cluster.private_subnet_ids
  cidr_blocks      = concat(module.eks_cluster.private_vpc_cidr_blocks, module.eks_cluster.public_vpc_cidr_blocks)
  instance_class   = "db.t3.medium"
  iam_auth_enabled = true

  depends_on = [module.eks_cluster]
}

resource "null_resource" "merge_kubeconfig" {

  depends_on = [module.eks_cluster, null_resource.aws_cli_check]

  provisioner "local-exec" {
    command = "aws eks --region ${var.region} update-kubeconfig --name ${var.camunda-cluster-name}"
    on_failure = fail
    when = create
  }
}

resource "null_resource" "aws_cli_check" {
 
  provisioner "local-exec" {
    command = "where aws"
    on_failure = fail
  }
}
*/