package com.devjsky.android.whereuat.widget.utils

import com.kakao.sdk.template.model.*


/**
 * ClassName            KotlinToJavaUtils
 * Created by JSky on   2022-02-22
 *
 * Description
 */
class KotlinToJavaUtils {

    companion object{
        fun getFeedTemplate(mem_name: String, meeting_address: String, mg_idx: String): FeedTemplate{
            val feedTemplate = FeedTemplate(
                    content = Content(
                            title = "우리 여기서 만나요~!",
                            description = "약속장소 : " + meeting_address,
                            imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                            link = Link(
                                    webUrl = "https://naver.com",
                                    mobileWebUrl = "https://naver.com"
                            )
                    ),
                    itemContent = ItemContent(
                            profileText = mem_name,
                            profileImageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png"

                    ),
                    buttons = listOf(

                            Button(
                                    "약속 맺기",
                                    Link(
                                            androidExecutionParams = mapOf("kakaoLinkType" to "meeting_agree", "mg_idx" to mg_idx),
                                            iosExecutionParams = mapOf("kakaoLinkType" to "meeting_agree", "mg_idx" to mg_idx)
                                    )
                            )
                    )
            )

            return feedTemplate
        }
    }


}