# CarboNata 🍝+🗡️
Measuring the energy and carbon usage of Camunda Workers.

## Develop
All test ressources are under `src/` and `tf/`. while the first folder contains the test workers and processes, the latter contains the Terraform files to setup a Camunda 8 cluster based on AWS EKS and deploys the neccessary resources.

## C8 on AWS EKS via Terraform
 <span style="color:red">WARNING: There is a bug inside the eks module provided by Camunda which MAY break the destroying of resources, in case that happens, you have to execute `terraform apply -destroy` twice</span>
- Make sure you have AWS CLI (and configured), Terraform and Zeebe CLI installed
- Clone Repo
- Use ``terraform init`` and ``terraform apply`` in the ``tf`` folder of the project
  
- (Camunda 8) When everything is finished you can access 
  - Camunda Operate via: ``kubectl port-forward -n camunda8 svc/camunda-platform-operate  8080:80`` and afterwards via ``localhost:8080`` and creds ``demo:demo``
  - Camunda Tasklist via: ``kubectl port-forward -n camunda8 svc/camunda-platform-tasklist  8081:80`` and afterwards via ``localhost:8081`` and creds ``demo:demo``
  - Camunda Zeebee Gateway via: ``kubectl port-forward -n camunda8 svc/camunda-platform-zeebe-gateway 26500:26500`` and afterwards via ``localhost:26500`` and creds ``demo:demo``
    - The Sample_Process and its worker should already be deployed, therefore you can kick off an instance via ``zbctl create instance Fibonacciprocess --insecure``
    - Alternatively, look at the job resource in the camunda namespace
  - Grafana via: ``kubectl port-forward -n monitoring svc/kube-prometheus-stackr-grafana 9102:80`` and afterwards via ``localhost:9102`` and creds ``admin:prom-operator``
