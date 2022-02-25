package com.devjsky.android.whereuat.social.kakao

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.startActivity

import com.devjsky.android.whereuat.common.Constants.TAG
import com.devjsky.android.whereuat.social.kakao.callback.KakaoSendLinkMsgCallback
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.model.*


/**
 * ClassName            KakaoSendLinkMsg
 * Created by JSky on   2022-02-21
 *
 * Description
 */
class KakaoSendLinkMsg {

    private lateinit var sendLinkMsgCallback: KakaoSendLinkMsgCallback
    val defaultFeed = FeedTemplate(
            content = Content(
                    title = "우리 여기서 만나요~!",
                    description = "약속장소 : 부산시 수영구 수영로 541-1",
                    imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                    link = Link(
                            webUrl = "https://developers.kakao.com",
                            mobileWebUrl = "https://developers.kakao.com"
                    )
            ),
            itemContent = ItemContent(
                    profileText = "유저 이름",
                    profileImageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png"

            ),
            buttons = listOf(

                    Button(
                            "약속 장소 확인",
                            Link(
                                    androidExecutionParams = mapOf("kakaoLinkType" to "meeting_agree", "mem_token" to "test1"),
                                    iosExecutionParams = mapOf("kakaoLinkType" to "meeting_agree", "mem_token" to "test1")
                            )
                    )
            )
    )


    constructor(sendTalkMsgCallback: KakaoSendLinkMsgCallback) {

        this.sendLinkMsgCallback = sendTalkMsgCallback;
    }

    fun sendMsg(context: Context, template: FeedTemplate) {
        // 카카오톡 친구 목록 가져오기
        Log.e(TAG, "sendMsg()")

// 카카오톡 설치여부 확인
        if (LinkClient.instance.isKakaoLinkAvailable(context)) {
            LinkClient.instance.defaultTemplate(context, template) { linkResult, error ->
                if (error != null) {
                    Log.e(TAG, "카카오링크 보내기 실패", error)
                    sendLinkMsgCallback.onError(error)
                } else if (linkResult != null) {
                    Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                    sendLinkMsgCallback.onSuccess(linkResult)
                    startActivity(context, linkResult.intent, null)

                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                    Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
                }
            }
        } else {
            // 카카오톡 미설치: 웹 공유 사용 권장
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.defaultTemplateUri(template)

            // CustomTabs으로 웹 브라우저 열기

            // 1. CustomTabs으로 Chrome 브라우저 열기
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                // Chrome 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabs으로 디바이스 기본 브라우저 열기
            try {
                KakaoCustomTabsClient.open(context, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 인터넷 브라우저가 없을 때 예외처리
            }
        }


    }


}