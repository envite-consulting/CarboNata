# CarboNata 🍝+🗡️

Measuring the energy and carbon usage of Camunda Workers.

<!-- TOC -->
- [🏗Develop](#develop)
  - [C8 on AWS EKS via Terraform](#c8-on-aws-eks-via-terraform)
- [📨Contact](#contact)
<!-- TOC -->

# 🏗Develop

All test ressources are under `src/` and `tf/`. while the first folder contains the test workers and processes, the latter contains the Terraform files to setup a Camunda 8 cluster based on AWS EKS and deploys the neccessary resources.

## C8 on AWS EKS via Terraform
> [!IMPORTANT]  
>  There is a bug inside the eks module provided by Camunda which MAY break the destroying of resources, in case that happens, you have to execute `terraform apply -destroy` twice

- Make sure you have AWS CLI (and configured), Terraform and Zeebe CLI installed
- Clone Repo
- Setting up the environments
  - Running C8 on the locally configured kubernetes cluster i.e. the cluster which is active via the ``.kubeconf`` file
    - Use ``terraform init`` and ``terraform apply`` in the ``tf/c8`` of the project
  - (Optional) If you want to use an AWS EKS cluster configured according to Camunda and setup your ``.kubeconf`` accordingly
    - Use ``terraform init`` and ``terraform apply`` in the ``tf/eks`` of the project and afterwards setup C8
- (Camunda 8) When everything is finished you can access the following
  - (🗡️ Important 🗡️) If you have Camunda Identity and Keycloak enabled you should keep the ports of these open via:
    - ``kubectl port-forward -n camunda svc/camunda-platform-keycloak 18080:80`` and ``kubectl port-forward -n camunda svc/camunda-platform-identity 8080:80`` afterwards via ``localhost:8083`` and creds ``demo:demo`` 
  - Camunda Operate via: 
    - ``kubectl port-forward -n camunda svc/camunda-platform-operate  8081:80`` and afterwards via ``localhost:8081`` and creds ``demo:demo``
  - Camunda Tasklist via: 
    - ``kubectl port-forward -n camunda svc/camunda-platform-tasklist  8082:80`` and afterwards via ``localhost:8082`` and creds ``demo:demo``
  - Camunda Zeebee Gateway via: 
    - ``kubectl port-forward -n camunda svc/camunda-platform-zeebe-gateway 26500:26500`` and afterwards via ``localhost:26500`` and creds ``demo:demo``
    - The Sample_Process and its worker should already be deployed, therefore you can kick off an instance via ``zbctl create instance Fibonacciprocess --insecure``
    - Alternatively, look at the job resource in the camunda namespace
  - Grafana via: 
    - ``kubectl port-forward -n monitoring svc/kube-prometheus-stackr-grafana 9102:80`` and afterwards via ``localhost:9102`` and creds ``admin:prom-operator``
  - Camunda Optimize via: 
    - ``kubectl port-forward -n camunda svc/camunda-platform-optimize 8083:8090`` and afterwards via ``localhost:8083`` and creds ``demo:demo``
  

# 📨Contact

If you have any questions or ideas feel free to start a [discussion](https://github.com/envite-consulting/CarboNata/discussions).

This open source project is being developed by [envite consulting GmbH](https://envite.de).

![envite consulting GmbH](assets/envite-black.png#gh-light-mode-only)
![envite consulting GmbH](assets/envite-white.png#gh-dark-mode-only)
