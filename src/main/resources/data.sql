# select * from status;
insert into HangOut.status(STATUS_TYPE)
values ('공개'),
       ('삭제'),
       ('관리자 삭제');

insert into hangout.report_reason(REPORT_TYPE)
values ('영리목적/홍보성'),
       ('개인정보 노출'),
       ('불법정보'),
       ('음란성/선정성'),
       ('욕설/인신공격'),
       ('아이디/DB거래'),
       ('같은 내용 반복'),
       ('기타');
