# sh

echo "Navigating to the corresponding directory (tf/cd)"
cd tf/c8 || exit

echo "Initializing terraform"
echo ""
terraform init

echo "Applying terraform"
echo ""
terraform apply -auto-approve

export GRAFANA_FORWORDED_PORT=9102
echo "Forward Grafana Port to $GRAFANA_FORWORDED_PORT"
echo ""
kubectl port-forward -n grafana svc/kube-prometheus-stack-grafana $GRAFANA_FORWORDED_PORT:80
