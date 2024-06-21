## 🚧 Configuration

Configure the application using [application.yml](./src/main/resources/application.yaml).

You can run the Connector and connect it to a Camunda Platform 8 SaaS cluster.

```yml
camunda:
  client:
    auth:
      client-id: xxx
      client-secret: xxx
    cluster-id: xxx
    region: xxx
```

If you're running Camunda Platform 8 Self-Managed then use the following configuration:

```yml
camunda:
  client:
    zeebe:
      base-url: http://127.0.0.1:26500
```

## 🐳 Docker

To build and push a docker container run the following command. 

> [!NOTE]
> Replace `X.X.X` with the version you want to publish


```shell
docker buildx build --push --platform linux/arm64/v8,linux/amd64 \
  -t enviteconsulting/carbonata-worker:latest \
  -t enviteconsulting/carbonata-worker:X.X.X \
  .
```
