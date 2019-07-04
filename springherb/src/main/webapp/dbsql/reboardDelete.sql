--reboardDelete.sql

/*
    exec reboardDelete(4,0,4);
*/
create or replace procedure  reboardDelete--프로시저 이름 
(
--매개변수
    p_no number,
    p_step number,
    p_groupNo number
)
is
--변수선언부
    cnt number;
begin
--처리할 내용
    --원본글인 경우
    if p_step=0 then
        select count(*) into cnt from reboard
        where groupno=p_groupno;
        
        
        --groupno가 같은 것 중 step이 1인 글이 삭제되면 
        --groupno=no인(원본글) 글 삭제처리 
        --답변이 있는 원본글인 경우 - delflag update
        
        
        if cnt>1 then
            update reboard
            set delflag='Y'
            where no=p_no;
        else
        --답변없는 원본글인 경우 - 삭제
        delete from reboard where no=p_no;
        end if;
    else
        --답변글인 경우 - 삭제
        delete from reboard 
        where no=p_no;
        
        select count(*) into cnt from reboard
        where groupno=p_groupno;
        
        if cnt=1 then
            delete from reboard a
            where exists(select 1 from reboard b where a.no=b.no and 
            groupno=p_groupno and delflag='Y');  
            --원래대로도 가능하지만 그렇게 되면 의미상 조건만족시 다 지우라는 의미가되버림
        end if;
    end if;
    commit;

EXCEPTION
    WHEN OTHERS THEN
	raise_application_error(-20001, '글 삭제 실패!');
        ROLLBACK;
end;
