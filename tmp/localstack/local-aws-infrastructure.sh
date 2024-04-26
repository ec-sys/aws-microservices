#!/bin/sh

awslocal sqs create-queue --queue-name order-customer-queue
awslocal sqs create-queue --queue-name order-inventory-queue
awslocal sqs create-queue --queue-name order-process-queue

awslocal sns create-topic --name order-process-topic
awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:order-process-topic" --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:order-inventory-queue"
awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:order-process-topic" --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:order-customer-queue"

awslocal ses verify-email-identity --email-address noreply@stratospheric.dev
awslocal ses verify-email-identity --email-address info@stratospheric.dev
awslocal ses verify-email-identity --email-address tom@stratospheric.dev
awslocal ses verify-email-identity --email-address bjoern@stratospheric.dev
awslocal ses verify-email-identity --email-address philip@stratospheric.dev

awslocal dynamodb create-table \
    --table-name local-todo-app-breadcrumb \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=10 \

echo "Initialized."
