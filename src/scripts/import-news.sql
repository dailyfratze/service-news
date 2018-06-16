INSERT INTO news.sn_posts (id, created_at, created_by, updated_at, updated_by, content)
SELECT n.id,
     n.created_on,
     c.login,
     n.updated_at,
     u.login,
     n.text
FROM imp.news n
JOIN imp.users c ON c.id = n.created_by_id
JOIN imp.users u ON u.id = n.updated_by_id
ORDER BY id ASC;
