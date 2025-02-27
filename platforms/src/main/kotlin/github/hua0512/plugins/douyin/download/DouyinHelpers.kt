/*
 * MIT License
 *
 * Stream-rec  https://github.com/hua0512/stream-rec
 *
 * Copyright (c) 2025 hua0512 (https://github.com/hua0512)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package github.hua0512.plugins.douyin.download

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.toErrorIfNull
import com.github.michaelbull.result.toResultOr
import github.hua0512.plugins.base.ExtractorError.InvalidExtractionUrl
import github.hua0512.plugins.douyin.download.DouyinExtractor.Companion.URL_REGEX
import io.ktor.client.request.*

/** Utils for Douyin requests
 * @author hua0512
 * @date : 2024/10/6 16:40
 */


/**
 * Extracts the Douyin webrid from the specified URL.
 *
 * @param url The URL from which to extract the Douyin webrid
 * @return the extracted Douyin webrid or an error if the URL is invalid
 */
internal fun extractDouyinWebRid(url: String): Result<String, InvalidExtractionUrl> {
  val roomIdPattern = URL_REGEX.toRegex()
  return url.takeIf { it.isNotEmpty() }.toResultOr {
    InvalidExtractionUrl
  }.map {
    roomIdPattern.find(url)?.groupValues?.get(1)
  }.toErrorIfNull {
    InvalidExtractionUrl
  }.map {
    it
  }
}

internal fun HttpRequestBuilder.fillDouyinCommonParams() {
  DouyinParams.commonParams.forEach { (t, u) ->
    parameter(t, u)
  }
}

internal fun HttpRequestBuilder.fillDouyinAppCommonParams() {
  DouyinParams.appCommonParams.forEach { (t, u) ->
    parameter(t, u)
  }
}

internal fun MutableMap<String, String>.fillDouyinCommonParams() {
  putAll(DouyinParams.commonParams)
}

internal fun MutableMap<String, String>.fillDouyinWsParams() {
  putAll(DouyinParams.websocketParams)
}

internal fun HttpRequestBuilder.fillWebRid(webRid: String) {
  parameter(DouyinParams.WEB_RID_KEY, webRid)
}

internal fun HttpRequestBuilder.fillSecUid(secUid: String) {
  parameter(DouyinParams.SEC_USER_ID_KEY, secUid)
}