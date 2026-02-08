-- KEYS[1] = rate limit key
-- ARGV[1] = limit
-- ARGV[2] = window (seconds)

-- 	•	INCR key
-- 	•	If first hit then set EXPIRE
-- 	•	If count > limit then return 0 (blocked)
-- 	•	Else will return remaining quota

local current = redis.call("INCR", KEYS[1])
if tonumber(current) == 1 then
    redis.call("EXPIRE", KEYS[1], ARGV[2])
end
if tonumber(current) > tonumber(ARGV[1]) then
    return 0
end
return tonumber(ARGV[1]) - tonumber(current)