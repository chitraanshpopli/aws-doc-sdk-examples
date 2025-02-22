//snippet-sourcedescription:[ListUserPoolClients.kt demonstrates how to list existing user pool clients that are available in the specified AWS Region in your current AWS account.]
//snippet-keyword:[AWS SDK for Kotlin]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon Cognito]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[08/01/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/


package com.kotlin.cognito

//snippet-start:[cognito.kotlin.ListUserPoolClients.import]
import aws.sdk.kotlin.services.cognitoidentityprovider.CognitoIdentityProviderClient
import aws.sdk.kotlin.services.cognitoidentityprovider.model.ListUserPoolClientsRequest
import aws.sdk.kotlin.services.cognitoidentity.model.CognitoIdentityException
import kotlin.system.exitProcess
//snippet-end:[cognito.kotlin.ListUserPoolClients.import]

suspend fun main(args: Array<String>){

    val usage = """
        Usage: <userPoolId>
    
        Where:
            userPoolId - the ID given to your user pool.
        """

    if (args.size != 1) {
          println(usage)
          exitProcess(0)
    }

    val userPoolId = args[0]
    val cognitoClient = CognitoIdentityProviderClient { region = "us-east-1" }
    listAllUserPoolClients(cognitoClient, userPoolId)
    cognitoClient.close()
}

//snippet-start:[cognito.kotlin.ListUserPoolClients.main]
suspend fun listAllUserPoolClients(cognitoClient: CognitoIdentityProviderClient, userPoolId: String) {

   try {

        val listUserPoolClientsRequest = ListUserPoolClientsRequest{
            this.userPoolId = userPoolId
        }

        val clientsResponse = cognitoClient.listUserPoolClients(listUserPoolClientsRequest)
        val pools = clientsResponse.userPoolClients
            if (pools != null) {
                for (userPoolClient in pools) {
                    println("Client ID is ${userPoolClient.clientId}")
                    println("Client Name ${userPoolClient.clientName}")
                }
            }

   } catch (ex: CognitoIdentityException) {
       println(ex.message)
       cognitoClient.close()
       exitProcess(0)
   }
 }
//snippet-end:[cognito.kotlin.ListUserPoolClients.main]