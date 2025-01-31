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

echo "destroying terraform"
echo ""
terraform destroy -auto-approve