package com.example.nutritionapp2ndedition

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class ChatRepository {
    // defines a new class by which we will be able to use the AI api and give it key directions.
    private val client = OkHttpClient()
    //private val apiKey = BuildConfig.API_KEY
    private val endpoint = "https://models.inference.ai.azure.com/chat/completions"
    private val model = "gpt-4o-mini"

    private val apiKey = BuildConfig.API_KEY.also {
        android.util.Log.d("CHAMELEON", "Key starts with: ${it.take(10)}")
    }

    private val systemPrompt = """
        You are Chameleon, a friendly nutrition assistant for young adults.
        Your job is to onboard the user by collecting three things in a natural conversation:
        1. Metadata: name, age, weight, height
        2. Goals: what they want to achieve nutritionally
        3. Context: anything extra like budget, food access, dietary restrictions
        
        Ask one question at a time. Be friendly and casual.
        Once you have all three categories, respond with exactly this format:
        
        (say this on a separate line word for word -> ) Onboarding Complete:
        
        NAME: [name]
        AGE: [age]
        WEIGHT: [weight]
        HEIGHT: [height] (after onboarding make sure its in [f'i] format where f is feet 
        and i is inches.
        GOALS: [(bulletpoint) goal #1
                (bulletpoint) goal #2
                (bulletpoint) goal #3]
        - attempt to ask for three main goals
        CONTEXT: [context] (if they say any equivalent to none then leave blank.)
        
        You are not to respond to any  queries that are related to topics other than the information you need to collect. This information
        is vital.
        
        After all the data is collected, also correct the grammar.
        
        If user is in the chat feature, you are able to answer any nutritional questions the user has.
        
       
        In the Main Screen if the user's data does not give you sufficient data to make a daily target, suggest general advice about nutrition and health and research every minute.
        
   
    """.trimIndent()
    // the following program deals with connecting the AI api system to the application
    suspend fun sendMessage(messages: List<ChatMessage>): String {
        return withContext(Dispatchers.IO) {
            val messagesArray = JSONArray()

            messagesArray.put(JSONObject().apply {
                put("role", "system")
                put("content", systemPrompt)
            })

            messages.forEach { msg ->
                messagesArray.put(JSONObject().apply {
                    put("role", if (msg.isUser) "user" else "assistant")
                    put("content", msg.text)
                })
            }

            val body = JSONObject().apply {
                put("model", model)
                put("messages", messagesArray)
            }.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext "Error: ${response.code} ${response.message}"
                }
                val responseBody = response.body?.string() ?: ""
                try {
                    val json = JSONObject(responseBody)
                    json.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                } catch (e: Exception) {
                    "Error: Failed to parse response"
                }
            }
        }
    }
    // this is for the daily targets ui on the home screen.
    suspend fun sendOneOffMessage(prompt: String): String {
        return withContext(Dispatchers.IO) {
            val messagesArray = JSONArray()

            messagesArray.put(JSONObject().apply {
                put("role", "user")
                put("content", prompt)
            })

            val body = JSONObject().apply {
                put("model", model)
                put("messages", messagesArray)
            }.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext "Error: ${response.code} ${response.message}"
                }
                val responseBody = response.body?.string() ?: ""
                try {
                    val json = JSONObject(responseBody)
                    json.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                } catch (e: Exception) {
                    "Error: Failed to parse response"
                }
            }
        }
    }
    //this is for the accessible chat function in the app if the user has any additional inquiries.
    suspend fun sendChatMessage(messages: List<ChatMessage>, userProfile: UserProfile): String {
        return withContext(Dispatchers.IO) {
            val messagesArray = JSONArray()

            messagesArray.put(JSONObject().apply {
                put("role", "system")
                put("content", """
                You are Chameleon, a friendly nutrition assistant.
                You already know this user's profile:
                Name: ${userProfile.name}
                Age: ${userProfile.age}
                Weight: ${userProfile.weight}
                Height: ${userProfile.height}
                Goals: ${userProfile.goals}
                Context: ${userProfile.context}
                
                Answer any nutrition questions they have. Be friendly, casual, and specific to their profile.
                Keep responses concise and practical.
            """.trimIndent())
            })

            messages.forEach { msg ->
                messagesArray.put(JSONObject().apply {
                    put("role", if (msg.isUser) "user" else "assistant")
                    put("content", msg.text)
                })
            }

            val body = JSONObject().apply {
                put("model", model)
                put("messages", messagesArray)
            }.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext "Error: ${response.code} ${response.message}"
                }
                val responseBody = response.body?.string() ?: ""
                try {
                    val json = JSONObject(responseBody)
                    json.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                } catch (e: Exception) {
                    "Error: Failed to parse response"
                }
            }
        }
    }
}
