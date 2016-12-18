CREATE TABLE category (
	id int primary key,
	title varchar(20)
);

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
	word varchar(50) primary key
);

CREATE TABLE all_words (
	word varchar(50) primary key,
	value int
);

CREATE TABLE sentiment (
	id int primary key,
	title varchar(50)
);

CREATE TABLE ordered_word_list (
	id bigint REFERENCES plain_tweet(id),
	hashtag char(100),
	impact_rate real,
	ordered_words char(160),
	category int REFERENCES category(id),
	sentiment int REFERENCES sentiment(id),
	primary key (id)
);