select DISTINCT ci_acct.acct_id, pr.address1, substr(pr.prem_type_cd, 1, 7)
from ci_acct
  join (select ci_prem.prem_id,
          ci_prem.prem_type_cd,
          ci_sa.acct_id,
          ci_prem.address1,

          row_number() over(Partition by ci_sa.acct_id order by ci_sa.start_dt desc) N

        from ci_sa
          join ci_prem
            on ci_sa.char_prem_id = ci_prem.prem_id
               and ci_sa.sa_type_cd like 'FLE%'
               and not ci_sa.sa_status_flg in ('10', '60', '70')) pr

    on pr.acct_id = ci_acct.acct_id
       and ci_acct.access_grp_cd like '01%'
       and ci_acct.mailing_prem_id <> pr.prem_id
