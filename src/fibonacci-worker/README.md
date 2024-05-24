## Configuration

Configure the application using [application.yml](./src/main/resources/application.yml).

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