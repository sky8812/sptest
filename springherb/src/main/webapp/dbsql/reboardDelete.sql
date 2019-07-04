--reboardDelete.sql

/*
    exec reboardDelete(4,0,4);
*/
create or replace procedure  reboardDelete--���ν��� �̸� 
(
--�Ű�����
    p_no number,
    p_step number,
    p_groupNo number
)
is
--���������
    cnt number;
begin
--ó���� ����
    --�������� ���
    if p_step=0 then
        select count(*) into cnt from reboard
        where groupno=p_groupno;
        
        
        --groupno�� ���� �� �� step�� 1�� ���� �����Ǹ� 
        --groupno=no��(������) �� ����ó�� 
        --�亯�� �ִ� �������� ��� - delflag update
        
        
        if cnt>1 then
            update reboard
            set delflag='Y'
            where no=p_no;
        else
        --�亯���� �������� ��� - ����
        delete from reboard where no=p_no;
        end if;
    else
        --�亯���� ��� - ����
        delete from reboard 
        where no=p_no;
        
        select count(*) into cnt from reboard
        where groupno=p_groupno;
        
        if cnt=1 then
            delete from reboard a
            where exists(select 1 from reboard b where a.no=b.no and 
            groupno=p_groupno and delflag='Y');  
            --������ε� ���������� �׷��� �Ǹ� �ǹ̻� ���Ǹ����� �� ������ �ǹ̰��ǹ���
        end if;
    end if;
    commit;

EXCEPTION
    WHEN OTHERS THEN
	raise_application_error(-20001, '�� ���� ����!');
        ROLLBACK;
end;
