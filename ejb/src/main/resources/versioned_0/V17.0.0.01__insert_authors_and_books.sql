
insert into author(first_name, second_name) VALUES
('Adam', 'Małysz'),
('Szakaron', 'Makaron'),
('Szakira', 'eee');

insert into library_book(available_copies_count, title, total_copies_count, author_id) values
(3, 'Daleko hoho', 3, (select id from author where first_name = 'Adam')),
(3, 'Daleko fruuu', 3, (select id from author where first_name = 'Adam')),
(3, 'Łaka Łaka', 3, (select id from author where first_name = 'Szakira')),
(3, 'This time for africa', 3, (select id from author where first_name = 'Szakira')),
(1, 'Makaron', 1, (select id from author where first_name = 'Szakaron')),
(1, 'Lazania', 1, (select id from author where first_name = 'Szakaron'));
