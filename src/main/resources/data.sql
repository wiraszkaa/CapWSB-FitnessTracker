-- Insert users first (no dependencies)
INSERT INTO
    users (id, birthdate, email)
VALUES (
        1,
        '1990-05-15',
        'john.doe@example.com'
    );

INSERT INTO
    users (id, birthdate, email)
VALUES (
        2,
        '1985-08-22',
        'jane.smith@example.com'
    );

INSERT INTO
    users (id, birthdate, email)
VALUES (
        3,
        '1992-11-30',
        'mike.johnson@example.com'
    );

INSERT INTO
    users (id, birthdate, email)
VALUES (
        4,
        '1988-03-10',
        'sarah.williams@example.com'
    );

INSERT INTO
    users (id, birthdate, email)
VALUES (
        5,
        '1995-07-18',
        'david.brown@example.com'
    );

-- Insert statistics (depends on users)
INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (1, 1, 25, 125.5, 3500);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (2, 2, 30, 150.0, 4200);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (3, 3, 15, 75.3, 2100);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (4, 4, 40, 200.8, 5600);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (5, 5, 20, 100.0, 2800);

-- Insert health metrics (depends on users)
INSERT INTO
    health_metrics (
        id,
        user_id,
        weight,
        height,
        heart_rate
    )
VALUES (1, 1, 75.5, 180.0, 72);

INSERT INTO
    health_metrics (
        id,
        user_id,
        weight,
        height,
        heart_rate
    )
VALUES (2, 2, 65.0, 165.0, 68);

INSERT INTO
    health_metrics (
        id,
        user_id,
        weight,
        height,
        heart_rate
    )
VALUES (3, 3, 82.3, 175.0, 75);

INSERT INTO
    health_metrics (
        id,
        user_id,
        weight,
        height,
        heart_rate
    )
VALUES (4, 4, 58.5, 160.0, 65);

INSERT INTO
    health_metrics (
        id,
        user_id,
        weight,
        height,
        heart_rate
    )
VALUES (5, 5, 90.0, 185.0, 80);