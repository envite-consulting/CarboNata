#!/bin/sh

echo "sourcing .env-File"
echo ""
set -a
. ./.env
set +a

if [ -z "${TF_VAR_CAMUNDA_CLUSTER_ID}" ];
then
    echo "You must set your local variable in the .env-File"
    echo ""
fi

echo "Navigating to the corresponding directory (tf/cd)"
cd tf/c8 || exit

echo "Initializing terraform"
echo ""
terraform init

echo "Applying terraform"
echo ""
terraform apply -auto-approve

echo "Forward Grafana Port to $GRAFANA_FORWORDED_PORT"
echo ""
kubectl port-forward -n grafana svc/kube-prometheus-stack-grafana $GRAFANA_FORWORDED_PORT:80 &> /dev/null &
