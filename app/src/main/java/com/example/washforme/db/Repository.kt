package com.example.washforme.db

import androidx.lifecycle.MutableLiveData
import com.example.washforme.model.*
import com.example.washforme.utils.Constants
import com.example.washforme.utils.MyPreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val preferences: MyPreferenceManager
) {
    suspend fun validatePhone(phoneNo: String): MutableLiveData<ResponseData<Boolean>> {
        val result = MutableLiveData<ResponseData<Boolean>>()

        val response = api.validatePhone(PhoneValidation(phoneNo))
        result.value = ResponseData.loading(null)
        try {
            if (response.isSuccessful) {
                val status = response.body()?.status ?: false
                if (status) {
                    result.value = ResponseData.success(true)
                } else {
                    result.value = ResponseData.failure(response.errorBody().toString(), null)
                }
            } else {
                result.value = ResponseData.failure("Invalid Server Error", null)
            }
        } catch (e: Exception) {
            result.value = ResponseData.failure("Internal Error", null)
        }
        return result
    }

    suspend fun validateOTP(
        phoneNo: String,
        otp: String
    ): MutableLiveData<ResponseData<Boolean>> {
        val result = MutableLiveData<ResponseData<Boolean>>()
        val response = api.validateOTP(OtpValidation(phoneNo, otp))
        result.value = ResponseData.loading(null)
        try {
            if (response.isSuccessful) {
                val token = response.body()?.token
                val tokenExpiry = response.body()?.expiry
                token?.let { preferences.set(Constants.TOKEN, it) }
                tokenExpiry?.let { preferences.set(Constants.EXPIRY, it) }
                result.value = ResponseData.success(true)
            } else {
                result.value = ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            result.value = ResponseData.failure("Internal Error", null)
        }
        return result
    }

    suspend fun logoutUser(): MutableLiveData<ResponseData<Boolean>> {
        val result = MutableLiveData<ResponseData<Boolean>>()
        val response = api.logoutUser("Token ${preferences.getString(Constants.TOKEN).toString()}")
        result.value = ResponseData.loading(null)
        try {
            if (response.isSuccessful) {
                preferences.clear()
                result.value = ResponseData.success(true)
            } else {
                result.value = ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            result.value = ResponseData.failure("Internal Error", null)
        }
        return result
    }

    suspend fun getCategories(): Flow<ResponseData<List<Category>>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response =
                api.getWashCategory("Token ${preferences.getString(Constants.TOKEN).toString()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseData.success(it))
                }
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        }
    }

    suspend fun getWashingItems(id: Int?= null): Flow<ResponseData<List<WashItem>>> = flow {
        emit(ResponseData.loading(null))
        try {
            val token = "Token ${preferences.getString(Constants.TOKEN)}"
            val response = if (id==null) api.getWashingItems(token) else api.getWashingItems(token, id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseData.success(it))
                }
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        }
    }

    suspend fun createUserWashRelation(categoryRelations: ArrayList<WashCategoryRelation>?): Flow<ResponseData<UserWashRelation>> = flow  {
        emit(ResponseData.loading(null))
        try {
            val response = api.createUserWashRelation("Token ${preferences.getString(Constants.TOKEN).toString()}", categoryRelations)

            if (response.isSuccessful) {
                emit(ResponseData.success(response.body()))
            } else {
                emit(ResponseData.failure(response.errorBody().toString(), null))
            }
        } catch (e: Exception) {
            emit(ResponseData.failure("Internal Error $e", null))
        }
    }

    fun getUser(): Flow<ResponseData<User>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response = api.getUserDetails("Token ${preferences.getString(Constants.TOKEN)}")

            if (response.isSuccessful) {
                emit(ResponseData.success(response.body()))
            } else {
                emit(ResponseData.failure(response.errorBody().toString(), null))
            }
        } catch (e: Exception) {
            emit(ResponseData.failure("Internal Error $e", null))
        }
    }

    suspend fun getAllAddresses(): Flow<ResponseData<List<UserAddress>>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response =
                api.getAllAddresses("Token ${preferences.getString(Constants.TOKEN)}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseData.success(it))
                }
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        }
    }

    suspend fun changeCurrentAddress(id: Int): Flow<ResponseData<UserAddress>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response =
                api.changeCurrentAddress(id, "Token ${preferences.getString(Constants.TOKEN)}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseData.success(it))
                }
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            ResponseData.failure(
                msg = "Oops, something went wrong!",
                data = null
            )
        }
    }

    suspend fun updateCurrentAddress(id: Int, userAddress: UserAddress): Flow<ResponseData<UserAddress>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response =
                api.updateCurrentAddress(id, userAddress, "Token ${preferences.getString(Constants.TOKEN)}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseData.success(it))
                }
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            ResponseData.failure(
                msg = "Oops, something went wrong!",
                data = null
            )
        }
    }

    suspend fun createNewAddress(userAddress: UserAddress): Flow<ResponseData<UserAddress>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response =
                api.createNewAddress(userAddress, "Token ${preferences.getString(Constants.TOKEN)}")
            if (response.isSuccessful) {
                emit(ResponseData.success(response.body()))
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            ResponseData.failure(
                msg = "Oops, something went wrong!",
                data = null
            )
        }
    }

    suspend fun getPickUpSlots(): Flow<ResponseData<List<PickUpSlots>>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response = api.getPickUpSlots("Token ${preferences.getString(Constants.TOKEN)}")
            if (response.isSuccessful) {
                emit(ResponseData.success(response.body()))
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            ResponseData.failure(
                msg = "Oops, something went wrong!",
                data = null
            )
        }
    }

    suspend fun getPickUpSlotsByDate(date: String): Flow<ResponseData<List<PickUpSlots>>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response = api.getPickUpSlotsByDate("Token ${preferences.getString(Constants.TOKEN)}", date)
            if (response.isSuccessful) {
                emit(ResponseData.success(response.body()))
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            ResponseData.failure(
                msg = "Oops, something went wrong!",
                data = null
            )
        }
    }
}