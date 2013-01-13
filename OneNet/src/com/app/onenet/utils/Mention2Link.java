package com.app.onenet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.widget.TextView;

import com.app.onenet.constant.Constants;

public class Mention2Link {
	public static void extractMention2Link(TextView v) {
		v.setAutoLinkMask(0);

		Pattern mentionsPattern = Pattern.compile("@(\\w+?)(?=\\W|$)(.)");
		String mentionsScheme = String.format("%s/?%s=", Constants.MENTIONS_SCHEMA,
				Constants.PARAM_UNAME);
		Linkify.addLinks(v, mentionsPattern, mentionsScheme, new MatchFilter() {

			@Override
			public boolean acceptMatch(CharSequence s, int start, int end) {

				return s.charAt(end - 1) != '.';
			}

		}, new TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				return match.group(1);
			}
		});

		Pattern trendsPattern = Pattern.compile("#(\\w+?)#");
		String trendsScheme = String.format("%s/?%s=", Constants.TRENDS_SCHEMA,
				Constants.PARAM_UID);
		Linkify.addLinks(v, trendsPattern, trendsScheme, null,
				new TransformFilter() {
					@Override
					public String transformUrl(Matcher match, String url) {
						return match.group(1);
					}
				});

	}

}
