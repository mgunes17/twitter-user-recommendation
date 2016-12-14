CREATE TABLE plain_tweet (
	id bigint primary key,
	tweet varchar(160),
	screen_name char(100),
	user_name char(100),
	favorite_count int,
	retweet_count int,
	created_date timestamp
);
