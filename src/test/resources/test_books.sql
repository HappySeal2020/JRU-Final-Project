/* insert authors */
INSERT INTO author (name)
VALUES ('Архангельский А. Я.')
ON CONFLICT (name) DO NOTHING;

INSERT INTO author (name)
VALUES ('Осипов Д. Л.')
ON CONFLICT (name) DO NOTHING;

INSERT INTO author (name)
VALUES ('Шумаков П. В.')
ON CONFLICT (name) DO NOTHING;

INSERT INTO author (name)
VALUES ('Дарахвелидзе П. Г.')
ON CONFLICT (name) DO NOTHING;

INSERT INTO author (name)
VALUES ('Марко Кэнту')
ON CONFLICT (name) DO NOTHING;


/* insert publishers */
INSERT INTO publisher (name, site)
VALUES ('ЗАО Издательство Бином', 'www.test.com')
ON CONFLICT (name) DO NOTHING;

INSERT INTO publisher (name, site)
VALUES ('Издательские решения по лицензии Ridero', 'www.test.com')
ON CONFLICT (name) DO NOTHING;

INSERT INTO publisher (name, site)
VALUES ('Издательство Нолидж', 'www.test.com')
ON CONFLICT (name) DO NOTHING;

INSERT INTO publisher (name, site)
VALUES ('БХВ-Петербург', 'www.test.com')
ON CONFLICT (name) DO NOTHING;

INSERT INTO publisher (name, site)
VALUES ('Питер', 'www.test.com')
ON CONFLICT (name) DO NOTHING;


/* insert books */
insert into book (name, author_id, print_year, publisher_id, bbk, isbn, pages) values
    ('Язык SQL в Delphi 5', (SELECT distinct id FROM author where name='Архангельский А. Я.'), 2000,
     (SELECT distinct id FROM publisher where name='ЗАО Издательство Бином'), '32.973-018.1', '5-7989-0116-5', 208)
ON CONFLICT (name) DO NOTHING;

insert into book (name, author_id, print_year, publisher_id, bbk, isbn, pages) values
    ('Delphi. Программирование для Android. Библиотека FireMonkey', (SELECT distinct id FROM author where name='Осипов Д. Л.'), 2016,
     (SELECT distinct id FROM publisher where name='Издательские решения по лицензии Ridero'), 'bbk', '978-5-4474-7944-2', 632)
ON CONFLICT (name) DO NOTHING;

insert into book (name, author_id, print_year, publisher_id, bbk, isbn, pages) values
    ('Delphi 3 и разработка приложений баз данных', (SELECT distinct id FROM author where name='Шумаков П. В.'), 1998,
     (SELECT distinct id FROM publisher where name='Издательство Нолидж'), '32.973-26-018.2', '5-89251-022-0', 704)
ON CONFLICT (name) DO NOTHING;

insert into book (name, author_id, print_year, publisher_id, bbk, isbn, pages) values
    ('Delphi - среда визуального программирования', (SELECT distinct id FROM author where name='Дарахвелидзе П. Г.'), 1996,
     (SELECT distinct id FROM publisher where name='БХВ-Петербург'), 'bbk', '5-85237-031-2', 352)
ON CONFLICT (name) DO NOTHING;

insert into book (name, author_id, print_year, publisher_id, bbk, isbn, pages) values
    ('Delphi 7: Для профессионалов', (SELECT distinct id FROM author where name='Марко Кэнту'), 2004,
     (SELECT distinct id FROM publisher where name='Питер'), '32.973-018.2', '5-94723-593-5', 1101)
ON CONFLICT (name) DO NOTHING;


/* insert links to authors */
insert into author_to_book (book_id, author_id) 
  select 
    (select distinct id from book where name='Язык SQL в Delphi 5'),
    (select distinct id from author where name='Архангельский А. Я.')
  where not exists (
    select 1 from author_to_book where
      book_id=(select distinct id from book where name='Язык SQL в Delphi 5') 
      and 
      author_id=(select distinct id from author where name='Архангельский А. Я.') );

insert into author_to_book (book_id, author_id) 
  select 
    (select distinct id from book where name='Delphi. Программирование для Android. Библиотека FireMonkey'),
    (select distinct id from author where name='Осипов Д. Л.')
  where not exists (
    select 1 from author_to_book where 
      book_id=(select distinct id from book where name='Delphi. Программирование для Android. Библиотека FireMonkey') 
      and 
      author_id=(select distinct id from author where name='Осипов Д. Л.'));

insert into author_to_book (book_id, author_id) 
  select 
    (select distinct id from book where name='Delphi 3 и разработка приложений баз данных'),
    (select distinct id from author where name='Шумаков П. В.')
  where not exists (
    select 1 from author_to_book where 
      book_id=(select distinct id from book where name='Delphi 3 и разработка приложений баз данных') 
      and 
      author_id=(select distinct id from author where name='Шумаков П. В.'));


insert into author_to_book (book_id, author_id) 
  select 
    (select distinct id from book where name='Delphi - среда визуального программирования'),
    (select distinct id from author where name='Дарахвелидзе П. Г.')
  where not exists (
    select 1 from author_to_book where 
      book_id=(select distinct id from book where name='Delphi - среда визуального программирования') 
      and 
      author_id=(select distinct id from author where name='Дарахвелидзе П. Г.'));


insert into author_to_book (book_id, author_id) 
  select 
    (select distinct id from book where name='Delphi 7: Для профессионалов'),
    (select distinct id from author where name='Марко Кэнту')
  where not exists (
    select 1 from author_to_book where 
      book_id=(select distinct id from book where name='Delphi 7: Для профессионалов') 
      and 
      author_id=(select distinct id from author where name='Марко Кэнту'));
