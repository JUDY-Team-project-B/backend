# select * from status;
use HangOut;
insert into HangOut.status(STATUS_TYPE)
values ('공개'),
       ('삭제'),
       ('관리자 삭제');