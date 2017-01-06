CREATE TABLE category (
	id int primary key,
	title varchar(20)
);

CREATE TABLE plain_tweet (
	id bigint primary key,
	tweet varchar(160),
	screen_name varchar(100),
	user_name varchar(100),
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
	hashtag varchar(100),
	impact_rate real,
	ordered_words varchar(160),
	category int REFERENCES category(id),
	sentiment int REFERENCES sentiment(id),
	primary key (id)
);

CREATE TABLE word_category_frequency(
	id int primary key,
	category int REFERENCES category(id),
	word varchar(100),
	count int
);

CREATE TABLE word_sentiment_frequency(
	id int primary key,
	sentiment int REFERENCES sentiment(id),
	word varchar(100),
	count int
);

CREATE TABLE account_analyze (
	user_name varchar(100),
	category int REFERENCES category(id),
	weight int NOT NULL,
	primary key(user_name, category)
);