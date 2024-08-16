kubectl port-forward -n camunda svc/camunda-platform-keycloak 18080:80 & \
kubectl port-forward -n camunda svc/camunda-platform-identity 8080:80 & \
kubectl port-forward -n camunda svc/camunda-platform-operate  8081:80 & \
kubectl port-forward -n camunda svc/camunda-platform-tasklist  8082:80 & \
kubectl port-forward -n camunda svc/camunda-platform-zeebe-gateway 26500:26500 & \
kubectl port-forward -n grafana svc/kube-prometheus-stack-grafana 9102:80 & \
kubectl port-forward -n camunda svc/camunda-platform-optimize 8083:80 &