CREATE TABLE plain_tweet (
	id bigint primary key,
	tweet varchar(160),
	screen_name char(100),
	user_name char(100),
	favorite_count int,
	retweet_count int,
	created_date timestamp
);

CREATE TABLE stop_words (
	word char(50) primary key
);

CREATE TABLE all_words (
	word char(50) primary key,
	value int
);

CREATE TABLE ordered_word_list (
	id bigint REFERENCES plain_tweet(id),
	hashtag char(100),
	impact_rate real,
	ordered_words char(160),
	primary key (id)
);

