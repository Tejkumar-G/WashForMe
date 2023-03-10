package com.example.washforme.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ModelHelper{
    @SerialName("id")
    var id : Int? = null

    @SerialName("phone")
    var phone : String?= null

    @SerialName("email")
    var email : String?= null
}

data class Categories(
    var id : Int?,
    var name : String?,
    var image : String?
)

data class WashingItems(
    var id : Int,
    var name : String,
    var image : String?
)

@Serializable
 class WashCategoryItemRelations{
    @SerialName("wash_category_relations")
    var washCategoryRelations: List<WashCategoryRelationList>? = null
}

@Serializable
class WashCategoryRelationList {
    @SerialName("category_id")
    var categoryId: Int? = null
    @SerialName("item_ids")
    var itemIds: List<Int?>? = null
}

data class CreateWashCategoryItemRelationsResponse (
    var id: Int?,
    var user: UserX?,
    var wash_category_relations: List<WashCategoryRelationX>?
    )

data class UserX(
    var email: String?,
    var id: Int?,
    var phone: String?
)

data class WashCategoryRelationX(
    var category: Category?,
    var id: Int?,
    var items: List<Item?>?
)

data class Item(
    var id: Int?,
    var image: Any?,
    var name: String?
)

class WashCategoryItemRelationsResponse : ArrayList<WashCategoryItemRelationsItem>()

data class WashCategoryItemRelationsItem(
    var category: Category?,
    var id: Int?,
    var items: List<Item>?
)

class UserWashRelation : ArrayList<UserWashRelationItem>()

data class UserWashRelationItem(
    var id: Int?,
    var user: UserX?,
    var wash_category_relations: List<WashCategoryRelationX>?
)

class GetTimeSlotResponse : ArrayList<GetTimeSlotsResponseItem>()

data class GetTimeSlotsResponseItem(
    var available: Boolean?,
    var available_quota: Int?,
    var date: String?,
    var end_time: String?,
    var filled_quota: Int?,
    var id: Int?,
    var schedule_completed: Boolean?,
    var start_time: String?
)

data class SlotBookingResponse(
    var id: Int?,
    var slot: Slot?,
    var user: UserX?,
    var user_wash_relation: UserWashRelation?
)

data class Slot(
    var available: Boolean?,
    var available_quota: Int?,
    var date: String?,
    var end_time: String?,
    var filled_quota: Int?,
    var id: Int?,
    var schedule_completed: Boolean?,
    var start_time: String?
)


